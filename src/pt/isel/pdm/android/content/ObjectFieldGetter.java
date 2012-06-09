package pt.isel.pdm.android.content;

public interface ObjectFieldGetter<O,F> {

	public F getFieldValueFrom(O obj);
}
