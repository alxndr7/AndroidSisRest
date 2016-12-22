package com.sisrest.bypsi.appsisrest;

/**
 * Created by Alexander on 8/12/2016.
 */

public class mComensal {

    private int nCodUsuCom;
    private int nCodCom;
    private String cNomCom;
    private String cApeCom;
    private String cDniCom;
    private String cTelCom;
    private String cDefault;

    public mComensal(int nCodUsuCom,String cNomCom, String cApeCom, String cDniCom, String cTelCom,String cDefault) {
        this.nCodUsuCom = nCodUsuCom;

        this.cNomCom = cNomCom;
        this.cApeCom = cApeCom;
        this.cDniCom = cDniCom;
        this.cTelCom = cTelCom;
        this.cDefault = cDefault;
    }

    public String getcTelCom() {
        return cTelCom;
    }

    public void setcTelCom(String cTelCom) {
        this.cTelCom = cTelCom;
    }

    public String getcDniCom() {
        return cDniCom;
    }

    public void setcDniCom(String cDniCom) {
        this.cDniCom = cDniCom;
    }

    public String getcApeCom() {
        return cApeCom;
    }

    public void setcApeCom(String cApeCom) {
        this.cApeCom = cApeCom;
    }

    public String getcNomCom() {
        return cNomCom;
    }

    public void setcNomCom(String cNomCom) {
        this.cNomCom = cNomCom;
    }

    public int getnCodUsuCom() {
        return nCodUsuCom;
    }

    public void setnCodUsuCom(int nCodUsuCom) {
        this.nCodUsuCom = nCodUsuCom;
    }

    public int getnCodCom() {
        return nCodCom;
    }

    public void setnCodCom(int nCodCom) {
        this.nCodCom = nCodCom;
    }

    public String getcDefault() {
        return cDefault;
    }

    public void setcDefault(String cDefault) {
        this.cDefault = cDefault;
    }

}
