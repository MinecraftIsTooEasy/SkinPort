package lain.mods.skinport.impl.forge;

import net.minecraft.ChatMessageComponent;
import org.spongepowered.asm.mixin.MixinEnvironment;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public enum SkinCustomization
{

    cape,
    jacket,
    left_sleeve,
    right_sleeve,
    left_pants_leg,
    right_pants_leg,
    hat;

    public static class SidedOptionalTupleKeyMap<K, V>
    {

        Map<MixinEnvironment.Side, Map<K, V>> map = new ConcurrentHashMap<>();

        public void clear(MixinEnvironment.Side side)
        {
            getMap(side).clear();
        }

        public V get(MixinEnvironment.Side side, K key)
        {
            return get(side, key, Optional.empty());
        }

        public V get(MixinEnvironment.Side side, K key, Optional<K> key2)
        {
            Map<K, V> m = getMap(side);
            V v = m.get(key);
            if (v == null && key2.isPresent())
                v = m.get(key2.get());
            return v;
        }

        private Map<K, V> getMap(MixinEnvironment.Side side)
        {
            if (!map.containsKey(side))
                map.putIfAbsent(side, new ConcurrentHashMap<>());
            return map.get(side);
        }

        public V put(MixinEnvironment.Side side, K key, Optional<K> key2, V value)
        {
            Map<K, V> m = getMap(side);
            V v = m.put(key, value);
            if (key2.isPresent())
                m.put(key2.get(), value);
            return v;
        }

        public V put(MixinEnvironment.Side side, K key, V value)
        {
            return put(side, key, Optional.empty(), value);
        }

        public V remove(MixinEnvironment.Side side, K key)
        {
            return remove(side, key, Optional.empty());
        }

        public V remove(MixinEnvironment.Side side, K key, Optional<K> key2)
        {
            Map<K, V> m = getMap(side);
            V v = m.remove(key);
            if (key2.isPresent())
                m.remove(key2.get());
            return v;
        }

    }

    private static final int _defaultFlags = of(values());

    public static final SidedOptionalTupleKeyMap<UUID, Integer> Flags = new SidedOptionalTupleKeyMap<>();
    public static int ClientFlags = getDefaultFlags();

    public static boolean contains(int flags, SkinCustomization... parts)
    {
        return (flags & of(parts)) != 0;
    }

    public static int getDefaultFlags()
    {
        return _defaultFlags;
    }

    public static int of(SkinCustomization... parts)
    {
        int flags = 0;
        for (SkinCustomization part : parts)
            flags |= part._flag;
        return flags;
    }

    private final ChatMessageComponent _displayName = ChatMessageComponent.createFromTranslationKey("options.modelPart." + name());
    private final int _flag = (int) Math.pow(2, ordinal());

    public ChatMessageComponent getDisplayName()
    {
        return _displayName;
    }

    public int getFlag()
    {
        return _flag;
    }

}
