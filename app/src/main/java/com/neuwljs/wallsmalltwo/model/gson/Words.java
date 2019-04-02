package com.neuwljs.wallsmalltwo.model.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Words {

    /**
     * log_id : 2471272194
     * words_result_num : 2
     * words_result : [{"words":" TSINGTAO"},{"words":"青島睥酒"}]
     */

    @SerializedName("words_result_num")
    private String wordsResultNum;

    @SerializedName("words_result")
    private List<WordsResultBean> wordsResult;

    public String getWordsResultNum() {
        return wordsResultNum;
    }

    public void setWordsResultNum(String wordsResultNum) {
        this.wordsResultNum = wordsResultNum;
    }

    public List<WordsResultBean> getWordsResult() {
        return wordsResult;
    }

    public void setWordsResult(List<WordsResultBean> wordsResult) {
        this.wordsResult = wordsResult;
    }

    public static class WordsResultBean {
        /**
         * words :  TSINGTAO
         */

        private String words;

        public String getWords() {
            return words;
        }

        public void setWords(String words) {
            this.words = words;
        }
    }
}
