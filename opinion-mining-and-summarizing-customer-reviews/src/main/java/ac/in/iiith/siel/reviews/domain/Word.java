package ac.in.iiith.siel.reviews.domain;

public class Word {

  private String text;

  private int startPostion;

  private int endPosition;

  private PennTreeBankPOSTag posTag;

  public String getText() {
    return text;
  }

  public void setText(final String text) {
    this.text = text;
  }

  public int getStartPostion() {
    return startPostion;
  }

  public void setStartPostion(final int startPostion) {
    this.startPostion = startPostion;
  }

  public int getEndPosition() {
    return endPosition;
  }

  public void setEndPosition(final int endPosition) {
    this.endPosition = endPosition;
  }

  public PennTreeBankPOSTag getPosTag() {
    return posTag;
  }

  public void setPosTag(final PennTreeBankPOSTag posTag) {
    this.posTag = posTag;
  }

}
