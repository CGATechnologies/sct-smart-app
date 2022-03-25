package app.sctp.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewbinding.ViewBinding;

import app.sctp.BuildConfig;
import app.sctp.R;
import app.sctp.core.ui.BindableFragment;
import app.sctp.core.ui.CardViewMenuItem;
import app.sctp.databinding.FragmentHomeBinding;
import app.sctp.user.UserDetails;

public class HomeFragment extends BindableFragment {

    private FragmentHomeBinding binding;
    private NavController navController;

    @Override
    protected ViewBinding bindViews(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return binding = FragmentHomeBinding.inflate(inflater);
    }

    @Override
    protected void initializeComponents(Bundle savedInstance) {
        binding.info.setText(format("v%s", BuildConfig.VERSION_NAME));
        binding.targetingCard.setOnClickListener(this::handleMenuCardClick);
        binding.administrationCard.setOnClickListener(this::handleMenuCardClick);

        UserDetails userDetails = getApplicationConfiguration().getUserDetails();
        binding.name.setText(userDetails.fullName());
        binding.district.setText(format("%s district", userDetails.getDistrictName()));
    }

    private void handleMenuCardClick(View v) {
        NavController navController = getNavController();
        if (v.getId() == R.id.targetingCard) {
            navController.navigate(R.id.targetingFragment);
        }
        Toast.makeText(requireContext(), "Clicked " + v, Toast.LENGTH_SHORT).show();
    }

    private NavController getNavController() {
        if (navController != null) {
            return navController;
        }
        return navController = Navigation.findNavController(binding.getRoot());
    }
}
