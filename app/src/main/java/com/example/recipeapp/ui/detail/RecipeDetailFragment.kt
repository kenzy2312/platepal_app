package com.example.recipeapp.ui.detail

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.app.RecipeApp
import com.example.recipeapp.ui.detail.RecipeDetailViewModel
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class RecipeDetailFragment : Fragment(R.layout.fragment_recipe_detail) {

    private val args: RecipeDetailFragmentArgs by navArgs()

    private val vm: RecipeDetailViewModel by viewModels {
        (requireActivity().application as RecipeApp).container.provideRecipeDetailVMFactory()
    }

    private lateinit var ivThumb: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var tvCategory: TextView
    private lateinit var tvInstructions: TextView
    private lateinit var expandArea: View
    private lateinit var btnExpand: View
    private lateinit var btnFavorite: MaterialButton
    private lateinit var btnPlayVideo: View
    private lateinit var miniPlayer: View
    private lateinit var webVideo: WebView
    private lateinit var btnCloseVideo: View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ivThumb = view.findViewById(R.id.ivThumb)
        tvTitle = view.findViewById(R.id.tvTitle)
        tvCategory = view.findViewById(R.id.tvCategory)
        tvInstructions = view.findViewById(R.id.tvInstructions)
        expandArea = view.findViewById(R.id.expandArea)
        btnExpand = view.findViewById(R.id.btnExpand)
        btnFavorite = view.findViewById(R.id.btnFavorite)
        btnPlayVideo = view.findViewById(R.id.btnPlayVideo)
        miniPlayer = view.findViewById(R.id.miniPlayer)
        webVideo = view.findViewById(R.id.webVideo)
        btnCloseVideo = view.findViewById(R.id.btnCloseVideo)

        btnExpand.setOnClickListener {
            expandArea.visibility = if (expandArea.visibility == View.GONE) View.VISIBLE else View.GONE
            (it as? MaterialButton)?.text =
                if (expandArea.visibility == View.VISIBLE) getString(R.string.hide_full_recipe)
                else getString(R.string.show_full_recipe)
        }

        btnFavorite.setOnClickListener { vm.toggleFavorite() }

        btnPlayVideo.setOnClickListener { showMiniPlayer() }
        btnCloseVideo.setOnClickListener { hideMiniPlayer() }

        // WebView basic settings for YouTube iframe
        webVideo.settings.javaScriptEnabled = true
        webVideo.settings.domStorageEnabled = true

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.ui.collect { state ->
                    when (state) {
                        is DetailUiState.Loading -> { /* show progress if you add one */ }
                        is DetailUiState.Loaded -> {
                            val d = state.detail
                            Glide.with(ivThumb).load(d.thumbUrl)
                                .placeholder(R.drawable.placeholder_recipe)
                                .into(ivThumb)
                            tvTitle.text = d.name
                            tvCategory.text = d.category ?: "—"
                            tvInstructions.text = d.instructions ?: "—"
                            btnFavorite.text =
                                if (state.isFavorite) getString(R.string.remove_favorite)
                                else getString(R.string.favorite)
                            // Prepare video URL for later
                            cachedEmbedUrl = buildYouTubeEmbedUrl(d.youtubeUrl)
                        }
                        is DetailUiState.Error -> { /* show an error message */ }
                    }
                }
            }
        }

        vm.load(args.mealId)
    }

    private var cachedEmbedUrl: String? = null

    private fun showMiniPlayer() {
        val url = cachedEmbedUrl ?: return
        miniPlayer.visibility = View.VISIBLE
        webVideo.loadData(
            """
            <html><body style="margin:0;padding:0;background:black;">
              <iframe width="100%" height="100%"
                src="$url"
                frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                allowfullscreen></iframe>
            </body></html>
            """.trimIndent(),
            "text/html",
            "utf-8"
        )
    }
    private fun hideMiniPlayer() {
        miniPlayer.visibility = View.GONE
        webVideo.loadUrl("about:blank")
    }

    private fun buildYouTubeEmbedUrl(youtubeUrl: String?): String? {
        if (youtubeUrl.isNullOrBlank()) return null
        val id = youtubeUrl.substringAfter("v=", "")
            .substringBefore("&")
            .ifBlank { youtubeUrl.substringAfterLast("/") } // fallback
        return "https://www.youtube.com/embed/$id?autoplay=1&playsinline=1"
    }
}