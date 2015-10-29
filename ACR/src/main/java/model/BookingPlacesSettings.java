package main.java.model;

/**
 * Created by Andrii.Bakhtiozin on 06.02.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class BookingPlacesSettings {

    //Class for addition place's settings for rail offers : double deck, sit from /sit to, window sit etc, possible position,coupeType etc.

    //----------------------------Deck number------------------------------------------
    private String deckNumber;
    //----------------------------Coupe Type------------------------------------------
    private String coupeType;

    public String getDeckNumber() {
        return deckNumber;
    }

    public BookingPlacesSettings withDeckNum(DeckNum deckNum) {
        this.deckNumber = deckNum.value;
        return this;
    }

    public String getCoupeType() {
        return coupeType;
    }

    public BookingPlacesSettings withCoupeType(CoupeType coupeType) {
        this.coupeType = coupeType.value;
        return this;
    }

    public enum DeckNum {
        FIRST("1"),
        SECOND("2");
        private String value;

        private DeckNum(String value) {
            this.value = value;
        }
    }

    public enum CoupeType {
        FEMALE("2"),
        MALE("4"),
        MIXED("6");
        private String value;

        private CoupeType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
}
