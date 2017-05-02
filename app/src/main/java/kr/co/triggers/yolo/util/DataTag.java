package kr.co.triggers.yolo.util;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

import kr.co.triggers.yolo.domain.Data;
import me.kaede.tagview.Tag;

public class DataTag{
    private String magenta = "#D43E4D";
    private String orange = "#FAA83F";
    private List<Data> list;

    public DataTag(List<Data> data){
        super();
        list = new ArrayList<>();
        list = data;
    }

    public List<Tag> getTags(){
        List<Tag> tags = new ArrayList<>();
        for(Data item : list){
            Tag tag = new Tag(item.getName());
            tag.layoutBorderSize = 1f;
            tag.radius = 60f;
            tag.tagTextSize = 14f;
            tag.layoutColor = Color.TRANSPARENT;
            if(item.getType().equalsIgnoreCase(Data.TYPE_GENRE)){
                tag.layoutBorderColor = Color.parseColor(orange);
                tag.tagTextColor = Color.parseColor(orange);
            }else{
                tag.layoutBorderColor = Color.parseColor(magenta);
                tag.tagTextColor = Color.parseColor(magenta);
            }
            tags.add(tag);
        }
        return tags;
    }
}
