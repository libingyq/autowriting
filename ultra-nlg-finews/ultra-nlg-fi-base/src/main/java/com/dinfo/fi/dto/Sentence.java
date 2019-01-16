package com.dinfo.fi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Date:2018/10/19</p>
 * <p>Module:</p>
 * <p>Description: </p>
 * <p>Remark: </p>
 *
 * @author wuxiangbo
 * @version 1.0
 *          <p>------------------------------------------------------------</p>
 *          <p> Change history</p>
 *          <p> Serial number: date:modified person: modification reason:</p>
 */
@Data
@Builder
@ToString
@NoArgsConstructor
public class Sentence {

    private String timeDesc;

    private List<SentenceElement> sentenceEle = new ArrayList<>();

    public void addSentenceElement(SentenceElement sentenceElement){

        sentenceEle.add(sentenceElement);
    }

    public Sentence(String timeDesc, List<SentenceElement> sentenceEle) {
        this.timeDesc = timeDesc;
        this.sentenceEle = sentenceEle;
    }
}


