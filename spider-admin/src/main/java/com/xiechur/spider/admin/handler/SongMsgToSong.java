package com.xiechur.spider.admin.handler;

import com.xiechur.spider.model.Song;
import com.xiechur.spider.model.vo.SongMsg;

import java.util.List;

/**
 * @author xiechurong
 * @Date 2018/9/3
 */
public interface SongMsgToSong {
    Song songMsgToSong(SongMsg songMsg);
    List<Song> list(List<SongMsg> songMsgs);

}
