package com.xiechur.spider.admin.crawl.impl;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.xiechur.spider.admin.crawl.Crawl;
import com.xiechur.spider.admin.handler.SongMsgToSong;
import com.xiechur.spider.constants.Constant;
import com.xiechur.spider.model.Album;
import com.xiechur.spider.model.Singer;
import com.xiechur.spider.model.Song;
import com.xiechur.spider.model.vo.SongMsg;
import com.xiechur.spider.service.AlbumService;
import com.xiechur.spider.service.MmsReportService;
import com.xiechur.spider.service.SingerService;
import com.xiechur.spider.service.SongService;
import com.xiechur.spider.util.CharacterUtils;
import com.xiechur.spider.util.HttpClientUtil;
import com.xiechur.spider.util.MiscUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.xiechur.spider.constants.Constant.ALBUM_DETAIL;
import static com.xiechur.spider.constants.Constant.SINGER_DETAIL;

/**
 * @author xiechurong
 * @Date 2018/9/5
 */
@Component
public class CrawlImpl implements Crawl {
    protected static final Logger LOGGER = LoggerFactory.getLogger(CrawlImpl.class);

    @Autowired
    private SingerService singerService;

    @Autowired
    private MmsReportService mmsReportService;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private SongMsgToSong songMsgToSong;

    @Autowired
    private SongService songService;
    private  String getAlbumListUrl(String singerId){
        return String.format(Constant.ALBUM_LIST,singerId);
    }
    private  String getSingerDetailUrl(String singerId){
        return String.format(SINGER_DETAIL,singerId);
    }
    private  String getAlbumDetailUrl(String albumId){
        return String.format(ALBUM_DETAIL,albumId);
    }
    public  void getAllSinger(){
        /**
         * 分类：华语男歌手，华语女歌手....
         */
        int[] type = {1001, 1002, 1003, 2001, 2002, 2003, 6001, 6002, 6003, 7001, 7002, 7003, 4001, 4002, 4003};
        // 字母
        int[] word = {-1, 0, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90};
        List<String> urls = new ArrayList<>();
        for (int i = 2; i < type.length; i++) {
            for (int j = 0; j < word.length; j++) {
                urls.add("http://music.163.com/discover/artist/cat?id=" + type[i] + "&initial=" + word[j]);
            }
        }
        for (String url : urls){
            String charest = "utf-8";
            Document document = getDocument(url,charest);
            Elements elements = document.select("[class=nm nm-icn f-thide s-fc0]");
            for (Element element : elements) {
                Elements hrefElements = element.select("a");
                String name = hrefElements.get(0).text();
                Attributes attributes = hrefElements.get(0).attributes();
                String href = attributes.get("href");
                if (href.startsWith("/artist?id=")) {
                    String idStr = href.substring(11);
//                    Singer singer = getSingerDetail(idStr);
                    Singer singer = new Singer();
                    singer.setSingerId(idStr);
                    singer.setName(name);
                    //重复过滤
                    if (!singerService.exist(idStr) && singer != null){
                        if(!singerService.add(singer)) {
                            LOGGER.error("add failed : "+singer.toString());
                        }
                    }else {
//                        if(!singerService.updateBySingerId(singer)){
//                            LOGGER.error("updateBySingerId failed:"+singer.toString());
//                        }
                    }

                    //获取专辑
                    List<Album> albums = getAlbumList(idStr);
                    if (CollectionUtils.isNotEmpty(albums)) {
                        for (Album album : albums) {
                            //TODO 重复过滤
                            if (StringUtils.isBlank(album.getAlbumId())){
                                LOGGER.error("albumId==null:"+album.toString());
                            }else {
                                System.out.println(album.toString());
                                if (!albumService.exist(album.getAlbumId())){
                                    albumService.add(album);
                                }else {
//                                    albumService.updateByAlbumId(album);
                                }
                            }
                            //TODO 保存专辑的评论

                            //TODO 保存专辑下的歌曲
                            List<Song> songList = getAlbumSongList(album.getAlbumId());
                            if (CollectionUtils.isNotEmpty(songList)) {
                                for (Song song : songList) {
                                    songService.add(song);
                                }
                            }
                            //TODO 保存歌曲下面的评论

                        }
                    }
                    System.out.println(singer.toString());
                }
            }
        }

    }

    public Singer getSingerDetail(String singerId){
        String url = getSingerDetailUrl(singerId);
        String charest = "utf-8";
        Document document = getDocument(url,charest);
        Elements singerName = document.select("#artist-name");
        if (StringUtils.isBlank(singerName.text())){
            return null;
        }
        System.out.println("歌手名字："+singerName.text());
        Elements desc = document.select("body > div.g-bd4.f-cb > div.g-mn4 > div > div > div:nth-child(3) > div > p:nth-child(2)");
        Elements image = document.select("body > div.g-bd4.f-cb > div.g-mn4 > div > div > div.n-artist.f-cb > img");
        String intro = desc.text();
        String picUrl = image.attr("src");
        Singer singer = new Singer();
        singer.setName(singerName.text());
        if(intro.length()>=10000 || "".equals(intro)){
            intro = "暂无介绍";
        }
        singer.setSingerId(singerId);
        singer.setIntro(intro);
        singer.setPicUrl(picUrl);
        return singer;
    }




    private static Document getDocument(String url, String charest) {
        List<Header> headerList = new ArrayList<>();
        headerList.add(new BasicHeader("Host", "music.163.com"));
        headerList.add(new BasicHeader("Referer", "https://music.163.com/"));
        headerList.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36"));
        String result = HttpClientUtil.doGet(url, headerList, charest);
        if(result!=null && !result.contains("n-for404")){
            return Jsoup.parse(result);
        }
        return null;
    }

    public List<Album> getAlbumList(String singerId){
        long beginTime = System.currentTimeMillis();
        List<Album> albumList = new ArrayList<>();
        String publishTime = null;
        String company = null;
        String url = getAlbumListUrl(singerId);
        String charest = "utf-8";
        Document document  = getDocument(url,charest);
        System.out.println("getDocument 耗时："+(System.currentTimeMillis()-beginTime));
        if(document!=null){
            Elements elements = document.select("#m-song-module >li");
            for(Element element :elements){
                //专辑链接
                long time2 = System.currentTimeMillis();
                Elements urlElement = element.select("[class=u-cover u-cover-alb3]");
                String albumUrl = urlElement.select(".msk").attr("href");
                //过滤剩下数字
                String albumId = MiscUtil.filter(albumUrl);
                if(!albumUrl.contains("https://")){
                    albumUrl = "https://music.163.com"+albumUrl;
                }
//                Elements elementsAlbumId = element.select("#m-song-module > li:nth-child(1) > div > a.icon-play.f-alpha");
//                String albumId = elementsAlbumId.attr("data-res-id");
                try {
                    Document albumDec = Jsoup.connect(albumUrl).
                            userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
                            .get();
                    Elements main = albumDec.select("body > div.g-bd4.f-cb.p-share > div.g-mn4 > div > div > div.m-info.f-cb > div.cnt > div > div.topblk");
                    Elements elementsName = main.select("div > div > h2");
                    String albumName = elementsName.text();
                    Elements elementsPublishTime = main.select("p:nth-child(3)");
                    if(elementsPublishTime!=null && !"".equals(elementsPublishTime.text()) ){
                        publishTime = elementsPublishTime.text().substring(5);
                    }
                    Elements elementsCompany = main.select("p:nth-child(4)");

                    if(elementsCompany!=null && !"".equals(elementsCompany.text()) ){
                        company = elementsCompany.text().substring(5);
                    }
                    //如果包含中文，则说明该字段为company
                    if(CharacterUtils.isContainChinese(publishTime)){
                        company = publishTime;
                        publishTime = null;
                    }

                    Elements elementsCommentId = albumDec.select("#cnt_comment_count");
                    String commentId = elementsCommentId.text();
                    if(CharacterUtils.isContainChinese(commentId)){
                        commentId = null;
                    }
                    Elements elementsImg = albumDec.select("body > div.g-bd4.f-cb.p-share > div.g-mn4 > div > div > div.m-info.f-cb > div.cover.u-cover.u-cover-alb > img");
                    String picUrl = elementsImg.attr("data-src");
                    Elements elementsIntro = albumDec.select("#album-desc-dot");

                    String intro = elementsIntro.text();
                    //补充
                    if (StringUtils.isBlank(intro)){
                        Elements elementsIntro1 = albumDec.select("[class=f-brk]");
                        intro =elementsIntro1.text();
                    }
                    if(intro.length()>=10000 || intro.length()<=1){
                        intro = "暂无介绍";
                    }
                    Album album = new Album();
                    album.setAlbumId(albumId);
                    album.setIntrodution(intro);
                    album.setCommentId(commentId);
                    album.setPicUrl(picUrl);
                    album.setName(albumName);
                    album.setSingerId(singerId);
                    album.setPublishCompany(company);
                    if(publishTime!=null){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = sdf.parse(publishTime);
                        album.setPublishTime(date);
                    }else{
                        album.setPublishTime(null);
                    }
                    System.out.println("获取单个专辑耗时："+(System.currentTimeMillis()-time2));

                    albumList.add(album);
                } catch (IOException e) {
                    e.printStackTrace();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }
        System.out.println("获取某个歌手的专辑耗时："+(System.currentTimeMillis()-beginTime));
        return albumList;
    }

    public List<Song> getAlbumSongList(String albumId){
        String url = getAlbumDetailUrl(albumId);
        String charest = "utf-8";
        Document document  = getDocument(url,charest);
        if(document!=null) {
            Elements elements = document.select("#song-list-pre-data");
            String resJson = elements.text();
//            logger.info(resJson);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<SongMsg>>() {}.getType();
            if(resJson!=null && !resJson.contains("html"));
            List<SongMsg> msgList = gson.fromJson(resJson, listType);
            return songMsgToSong.list(msgList);
        }
        return null;
    }
}
