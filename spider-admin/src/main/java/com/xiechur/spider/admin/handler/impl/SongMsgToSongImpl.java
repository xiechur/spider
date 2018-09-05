package com.xiechur.spider.admin.handler.impl;

import com.xiechur.spider.admin.handler.SongMsgToSong;
import com.xiechur.spider.model.Singer;
import com.xiechur.spider.model.Song;
import com.xiechur.spider.model.vo.SongMsg;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiechurong
 * @Date 2018/9/3
 */
@Component
public class SongMsgToSongImpl implements SongMsgToSong {

    @Override
    public Song songMsgToSong(SongMsg songMsg) {
        if (songMsg == null) {
            return null;
        }
        Song song = null;
        try {
            song =  new Song();
            song.setAlbumId(songMsg.getAlbum().getId()+"");
            song.setCommentId(songMsg.getCommentThreadId());
            song.setSongId(songMsg.getId()+"");
            song.setMp3Url("http://music.163.com/song/media/outer/url?id="+songMsg.getId());
            String ids = "";
            if (CollectionUtils.isNotEmpty(songMsg.getArtists())) {
                for (Singer singer : songMsg.getArtists()) {
                    Integer singerId = singer.getId();
                    ids += singerId+",";
                }
            }
            song.setSingerId(ids);
        }catch (Exception e) {
            song = null;
            e.printStackTrace();
        }finally {
            return song;
        }
    }

    @Override
    public List<Song> list(List<SongMsg> songMsgs) {
        if (CollectionUtils.isEmpty(songMsgs)) {
            return null;
        }
        List<Song> songs = new ArrayList<>();
        for (SongMsg songMsg : songMsgs) {
            Song song = songMsgToSong(songMsg);
            if (song != null){
                songs.add(song);
            }
        }
        return songs;
    }
}
