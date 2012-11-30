package br.com.bhgervendas.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 *
 * @author Eduardo Hernandorena
 */
public class ListaSync {

    @Expose
    @SerializedName("sync")
    List<Sync> sync;

    public List<Sync> getList() {
        return sync;
    }

    public void setList(List<Sync> list) {
        this.sync = list;
    }
}
