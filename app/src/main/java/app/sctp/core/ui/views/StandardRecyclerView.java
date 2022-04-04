package app.sctp.core.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import app.sctp.R;
import app.sctp.databinding.StandardRecyclerviewBinding;

public class StandardRecyclerView extends RelativeLayout {
    private ListObserver listObserver;
    private StandardRecyclerviewBinding binding;

    public StandardRecyclerView(Context context) {
        super(context);
        init(context, null);
    }

    public StandardRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public StandardRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public StandardRecyclerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        binding = StandardRecyclerviewBinding.inflate(LayoutInflater.from(context), this, true);

        binding.recyclerView
                .addItemDecoration(new DividerItemDecoration(getContext(),
                        DividerItemDecoration.VERTICAL));

        CharSequence emptyLabel = null;
        CharSequence filterHint = null;
        boolean allowFiltering = true;

        if (attributeSet != null) {
            TypedArray typedArray = context.obtainStyledAttributes(R.styleable.StandardRecyclerView);
            emptyLabel = typedArray.getText(R.styleable.StandardRecyclerView_empty_label);
            filterHint = typedArray.getText(R.styleable.StandardRecyclerView_filter_hint);
            allowFiltering = typedArray.getBoolean(R.styleable.StandardRecyclerView_allow_filter, true);
            typedArray.recycle();
        }

        if (emptyLabel == null) {
            emptyLabel = context.getText(R.string.default_empty_label_text);
        }

        if (filterHint == null) {
            filterHint = context.getText(R.string.default_filter_hint);
        }

        binding.emptyText.setText(emptyLabel);
        binding.searchEditInputText.setHint(filterHint);
        binding.searchFilter.setVisibility(allowFiltering ? VISIBLE : GONE);

        listObserver = new ListObserver(this);
    }

    public void setAdapter(RecyclerView.Adapter<?> adapter) {
        RecyclerView.Adapter<?> oldAdapter = binding.recyclerView.getAdapter();
        if (oldAdapter != null && oldAdapter.hasObservers()) {
            oldAdapter.unregisterAdapterDataObserver(listObserver);
        }
        binding.recyclerView.setAdapter(adapter);
        adapter.registerAdapterDataObserver(listObserver);
    }

    private class ListObserver extends RecyclerView.AdapterDataObserver {
        private final StandardRecyclerView recyclerView;

        ListObserver(StandardRecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        @Override
        public void onChanged() {
            updateViews();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            updateViews();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
            updateViews();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            updateViews();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            updateViews();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            updateViews();
        }

        @SuppressWarnings("ConstantConditions")
        private void updateViews() {
            boolean isEmpty = recyclerView.binding.recyclerView.getAdapter().getItemCount() == 0;
            binding.viewFlipper.setDisplayedChild(isEmpty ? 0 : 1);
        }
    }
}
