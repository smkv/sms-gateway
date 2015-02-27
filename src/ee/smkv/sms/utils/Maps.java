package ee.smkv.sms.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class Maps {

    public static <K, V> Builder<K, V> builder() {
        return new Builder<K, V>(new LinkedHashMap<K, V>());
    }

    public static class Builder<K, V> {
        private final Map<K, V> map;

        private Builder(Map<K, V> map) {
            this.map = map;
        }

        public Builder<K, V> put(K key, V value) {
            map.put(key, value);
            return this;
        }

        public Map<K, V> build() {
            return map;
        }
    }
}
