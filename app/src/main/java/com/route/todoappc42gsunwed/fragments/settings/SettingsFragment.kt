package com.route.todoappc42gsunwed.fragments.settings

import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import com.route.todoappc42gsunwed.R
import com.route.todoappc42gsunwed.databinding.FragmentSettingsBinding
import com.route.todoappc42gsunwed.shared_preferences.SettingsSharedPreferences

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var sharedPreferences: SettingsSharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = SettingsSharedPreferences(requireContext())

        val languages = resources.getStringArray(R.array.language_items)
        binding.languageAutocomplete.setText(
            if (sharedPreferences.getLanguage() == "en") languages[0] else languages[1],
            false
        )

        binding.languageAutocomplete.setOnItemClickListener { parent, _, position, _ ->
            val selectedLanguage = parent.getItemAtPosition(position).toString()
            val languageCode = if (selectedLanguage == languages[0]) "en" else "ar"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requireContext().getSystemService(android.app.LocaleManager::class.java)
                    ?.applicationLocales = LocaleList.forLanguageTags(languageCode)
            } else {
                AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.forLanguageTags(
                        languageCode
                    )
                )
            }
            sharedPreferences.setLanguage(languageCode)

        }

        val modes = resources.getStringArray(R.array.mode_items)
        binding.modeAutocomplete.setText(
            if (sharedPreferences.getMode() == "light") modes[0] else modes[1],
            false
        )

        binding.modeAutocomplete.setOnItemClickListener { parent, _, position, _ ->
            val selectedMode = parent.getItemAtPosition(position).toString()
            val modeValue = if (selectedMode == modes[0]) "light" else "dark"
            sharedPreferences.setMode(modeValue)
            AppCompatDelegate.setDefaultNightMode(if (modeValue == "light") MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}
