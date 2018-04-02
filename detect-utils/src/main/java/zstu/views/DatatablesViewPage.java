package zstu.views;

import java.util.List;

/**
 * Created by Aning.
 */
public class DatatablesViewPage<T> {

    private List<T> data; //aaData 与datatales 加载的“dataSrc"对应
    private long recordsTotal;
    private long recordsFiltered;
    private int draw;
    public DatatablesViewPage() {

    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }
}