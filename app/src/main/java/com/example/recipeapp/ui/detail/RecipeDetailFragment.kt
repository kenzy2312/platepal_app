package com.example.recipeapp.ui.detail

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.ImageButton
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
    private lateinit var btnFavorite: ImageButton
    private lateinit var btnPlayVideo: View
    private lateinit var miniPlayer: View
    private lateinit var webVideo: WebView
    private lateinit var btnCloseVideo: View
    private lateinit var labelExpand: TextView
    private lateinit var labelPlay: TextView
    private lateinit var labelFavorite: TextView

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
        labelExpand = view.findViewById(R.id.labelExpand)
        labelPlay = view.findViewById(R.id.labelPlay)
        labelFavorite = view.findViewById(R.id.labelFavorite)

        btnExpand.setOnClickListener {
            val expanded = expandArea.visibility == View.VISIBLE
            expandArea.visibility = if (expanded) View.GONE else View.VISIBLE
            // Update label + contentDescription
            labelExpand.text = if (expanded) getString(R.string.show_full_recipe)
            else getString(R.string.hide_full_recipe)
            btnExpand.contentDescription = labelExpand.text
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
                        is DetailUiState.Loading -> { /* show progress */ }
                        is DetailUiState.Loaded -> {
                            val d = state.detail
                            Glide.with(ivThumb).load(d.thumbUrl)
                                .placeholder(R.drawable.placeholder_recipe)
                                .into(ivThumb)
                            tvTitle.text = d.name
                            tvCategory.text = d.category ?: "—"
                            tvInstructions.text = d.instructions ?: "—"
                            labelFavorite.text =
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