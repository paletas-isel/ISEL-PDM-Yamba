package pt.isel.pdm.utils;

public class BaseKeyValueObject<K,V> implements KeyValueObject<K, V>{

	private final K _key;
	
	private final V _value;
	
	public BaseKeyValueObject(K key, V value)
	{
		this._key = key;
		this._value = value;
	}

	@Override
	public K getKey() {
		return _key;
	}

	@Override
	public V getValue() {
		return _value;
	}
}
