package App.vo;

public class Word {
    private String word;
    private String type;
    private String mean;
    private String sentence;
    private String interpret;
    private String phrase;

    public Word(){ }

    public Word(String word,String type,String mean,String sentence,String interpret,String phrase){
        this.word=word;
        this.type=type;
        this.mean=mean;
        this.sentence=sentence;
        this.interpret=interpret;
        this.phrase=phrase;
    }


    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getInterpret() {
        return interpret;
    }

    public void setInterpret(String interpret) {
        this.interpret = interpret;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

}
