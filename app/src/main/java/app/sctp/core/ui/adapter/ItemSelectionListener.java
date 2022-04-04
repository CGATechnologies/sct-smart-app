package app.sctp.core.ui.adapter;

public interface ItemSelectionListener<T> {
    void onItemSelected(T item);

    void onItemLongSelected(T item);
}
