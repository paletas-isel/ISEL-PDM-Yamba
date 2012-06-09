package pt.isel.pdm.android.content;

import pt.isel.pdm.utils.KeyValueObject;
import pt.isel.pdm.utils.Preconditions;
import android.content.ContentValues;

public class TemplateObjectToContentValueAdapter<T> implements ObjectToContentValueAdapter<T> {
	
	private final Iterable<KeyValueObject<String, ObjectFieldGetter<T, ?>>> _fields;
	
	public TemplateObjectToContentValueAdapter(Iterable<KeyValueObject<String, ObjectFieldGetter<T, ?>>> fields)
	{
		Preconditions.checkArgument(fields != null);
		
		this._fields = fields;
	}
	
	@Override
	public ContentValues adapt(T instance) {
		
		ContentValues content = new ContentValues();
		
		for(KeyValueObject<String, ObjectFieldGetter<T, ?>> elem:_fields)
		{
			if(elem != null && elem.getKey() != null)
			{
				if(elem.getValue() != null)
				{
					Object field = elem.getValue().getFieldValueFrom(instance);
					
					if(field != null)
					{
						ContentValuesExtension.put(content, elem.getKey(), field);
					}
					else
					{
						content.putNull(elem.getKey());
					}
				}
			}
		}
		
		return content;
	}
}
