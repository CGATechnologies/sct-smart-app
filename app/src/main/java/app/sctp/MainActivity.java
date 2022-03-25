package app.sctp;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewbinding.ViewBinding;

import app.sctp.core.ui.BindableActivity;
import app.sctp.databinding.ActivityMainBinding;

public class MainActivity extends BindableActivity {

    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected ViewBinding bindViews(LayoutInflater inflater) {
        return binding = ActivityMainBinding.inflate(inflater);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.host_fragment);

        navController = navHostFragment.getNavController();
    }
}
