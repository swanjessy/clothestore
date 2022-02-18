package com.example.clothestore.presentation.component

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.clothestore.R
import com.example.clothestore.data.model.Product
import com.example.clothestore.databinding.LayoutSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class UiBottomSheet : BottomSheetDialogFragment() {

    private var _binding: LayoutSheetBinding? = null
    private val binding get() = _binding!!
    var _listener: BottomSheetClickListener? = null
    private var product: Product? = null

    private var buttonLayoutParams: ConstraintLayout.LayoutParams? = null
    private var collapsedMargin = 0
    private var buttonHeight = 0
    private var expandedHeight = 0

    companion object {
        const val TAG = "UiBottom Sheet"
        fun newInstance(product: Product) = UiBottomSheet().apply {
            arguments = Bundle().apply {
                putParcelable("product", product)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null)
            product = arguments!!.getParcelable("product")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LayoutSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(binding.productImage.context).load(product?.image)
            .into(binding.productImage)

        //Todo Move to string resource
        binding.productPrice.text = "$${product?.price.toString()}"
        binding.productActualPriceTv.text =
            context?.getString(
                R.string.pro_details_actual_strike_value,
                product?.oldPrice.toString()
            )
        binding.tvName.text = product?.name
        binding.tvCategory.text = product?.category
        binding.tvStock.text = product?.stock.toString()

        binding.btnWishlist.setOnClickListener {
            _listener?.onItemWishList(product!!)
        }

        binding.btnAddToCart.setOnClickListener {
            _listener?.onItemAddToCart(product!!)
        }

        binding.imageRight.setOnClickListener {
            dismiss()
        }


    }

    interface BottomSheetClickListener {
        fun onItemWishList(product: Product)
        fun onItemAddToCart(product: Product)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { dialogInterface: DialogInterface? ->
            (dialogInterface as BottomSheetDialog?)?.let {
                setupRatio(
                    it
                )
            }
        }
        (dialog as BottomSheetDialog).behavior.addBottomSheetCallback(object :
            BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {}
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (slideOffset > 0) //Sliding happens from 0 (Collapsed) to 1 (Expanded) - if so, calculate margins
                    buttonLayoutParams!!.topMargin =
                        ((((expandedHeight - buttonHeight) - collapsedMargin) * slideOffset + collapsedMargin).toInt())
                else  //If not sliding above expanded, set initial margin
                    buttonLayoutParams!!.topMargin = collapsedMargin

                binding.footer.layoutParams = buttonLayoutParams
            }
        })
        return dialog
    }


    private fun setupRatio(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet = bottomSheetDialog.findViewById<FrameLayout>(R.id.design_bottom_sheet)
            ?: return

        //Retrieve button parameters
        buttonLayoutParams = binding.footer.layoutParams as ConstraintLayout.LayoutParams?

        //Retrieve bottom sheet parameters
        BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_COLLAPSED
        val bottomSheetLayoutParams = bottomSheet.layoutParams
        bottomSheetLayoutParams.height = getBottomSheetDialogDefaultHeight()

        expandedHeight = bottomSheetLayoutParams.height
        val peekHeight = (expandedHeight / 1.3).toInt()

        //Setup bottom sheet
        bottomSheet.layoutParams = bottomSheetLayoutParams
        BottomSheetBehavior.from(bottomSheet).skipCollapsed = false
        BottomSheetBehavior.from(bottomSheet).peekHeight = peekHeight
        BottomSheetBehavior.from(bottomSheet).isHideable = true

        //Calculate button margin from top
        buttonHeight =
            binding.footer.height + 40 //How tall is the button + experimental distance from bottom (Change based on your view)
        collapsedMargin = peekHeight - buttonHeight //Button margin in bottom sheet collapsed state
        buttonLayoutParams?.topMargin = collapsedMargin
        binding.footer.layoutParams = buttonLayoutParams

        //OPTIONAL - Setting up margins
        val recyclerLayoutParams =
            binding.scrollableContents.layoutParams as ConstraintLayout.LayoutParams
        val k: Float =
            (buttonHeight - 60) / buttonHeight.toFloat() //60 is amount that you want to be hidden behind button
        recyclerLayoutParams.bottomMargin =
            (k * buttonHeight).toInt() //Recyclerview bottom margin (from button)
        binding.scrollableContents.layoutParams = recyclerLayoutParams
    }

    //Calculates height for 90% of fullscreen
    private fun getBottomSheetDialogDefaultHeight(): Int {
        return getWindowHeight() * 90 / 100
    }

    //Calculates window height for fullscreen use
    private fun getWindowHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (requireContext() as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

}