package de.predic8.f_streams;

public class Verkauf {

    private long ware;
    private int menge;
    private String warengruppe;
    private String markt;

    public Verkauf(long ware, int menge, String warengruppe, String markt) {
        this.ware = ware;
        this.menge = menge;
        this.warengruppe = warengruppe;
        this.markt = markt;
    }

    public Verkauf() {
    }

    @Override
    public String toString() {
        return "Verkauf{" +
                "ware=" + ware +
                ", menge=" + menge +
                ", warengruppe='" + warengruppe + '\'' +
                ", markt='" + markt + '\'' +
                '}';
    }

    public long getWare() {
        return ware;
    }

    public void setWare(long ware) {
        this.ware = ware;
    }

    public int getMenge() {
        return menge;
    }

    public void setMenge(int menge) {
        this.menge = menge;
    }

    public String getWarengruppe() {
        return warengruppe;
    }

    public void setWarengruppe(String warengruppe) {
        this.warengruppe = warengruppe;
    }

    public String getMarkt() {
        return markt;
    }

    public void setMarkt(String markt) {
        this.markt = markt;
    }
}
