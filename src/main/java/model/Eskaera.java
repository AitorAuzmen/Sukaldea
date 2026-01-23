package model;

import java.time.LocalDateTime;
import java.util.List;

public class Eskaera {
    private final int id;
    private final Integer mahaiaZenbakia;
    private final LocalDateTime sortzeData;
    private final List<EskaeraItem> items;

    public Eskaera(int id, Integer mahaiaZenbakia, LocalDateTime sortzeData, List<EskaeraItem> items) {
        this.id = id;
        this.mahaiaZenbakia = mahaiaZenbakia;
        this.sortzeData = sortzeData;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public Integer getMahaiaZenbakia() {
        return mahaiaZenbakia;
    }

    public LocalDateTime getSortzeData() {
        return sortzeData;
    }

    public List<EskaeraItem> getItems() {
        return items;
    }
}
