package pt.isel.pdm.patterns.adapter;

public interface Adapter<I,O> {

	public O adapt(I instance);
}
