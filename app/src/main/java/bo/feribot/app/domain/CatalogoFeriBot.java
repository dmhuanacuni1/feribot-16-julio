package bo.feribot.app.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Fotografía del catálogo local que consume el motor. No depende de Android ni de Room.
 */
public class CatalogoFeriBot {

    private final List<ItemCatalogo> items;
    private final List<Sinonimo> sinonimos;

    public CatalogoFeriBot(List<ItemCatalogo> items, List<Sinonimo> sinonimos) {
        this.items = Collections.unmodifiableList(new ArrayList<>(items));
        this.sinonimos = Collections.unmodifiableList(new ArrayList<>(sinonimos));
    }

    public List<ItemCatalogo> items() {
        return items;
    }

    public List<Sinonimo> sinonimos() {
        return sinonimos;
    }

    public List<String> rubros() {
        Set<String> rubros = new LinkedHashSet<>();
        for (ItemCatalogo item : items) {
            if (item.rubro != null && !item.rubro.isEmpty()) {
                rubros.add(item.rubro);
            }
        }
        return new ArrayList<>(rubros);
    }
}
