package pt.isel.pdm.java.lang;

import java.util.Iterator;

import pt.isel.pdm.patterns.adapter.Adapter;
import pt.isel.pdm.utils.Preconditions;

public class LazyLoadIterableAdapter<I,O> implements Iterable<O> {

	private final Iterable<I> _iterable;
	
	private final Adapter<I,O> _adapter;
	
	public LazyLoadIterableAdapter(Iterable<I> iterable, Adapter<I,O> adapter)
	{
		Preconditions.checkArgument(iterable != null);
		Preconditions.checkArgument(adapter != null);
		
		this._iterable = iterable;
		this._adapter = adapter;
	}
	
	@Override
	public Iterator<O> iterator() {
		
		final Iterator<I> iterator = _iterable.iterator();
		
		return new Iterator<O>()
		{
			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public O next() {
				return _adapter.adapt(iterator.next());
			}

			@Override
			public void remove() {
				iterator.remove();				
			}
			
		};
	}
}
