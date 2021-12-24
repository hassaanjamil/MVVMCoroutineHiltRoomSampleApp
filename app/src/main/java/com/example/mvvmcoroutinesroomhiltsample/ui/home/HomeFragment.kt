package com.example.mvvmcoroutinesroomhiltsample.ui.home

import android.annotation.SuppressLint
import android.graphics.drawable.InsetDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.MenuRes
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.PopupMenu
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmcoroutinesroomhiltsample.R
import com.example.mvvmcoroutinesroomhiltsample.data.local.entity.Note
import com.example.mvvmcoroutinesroomhiltsample.data.model.ApiUser
import com.example.mvvmcoroutinesroomhiltsample.databinding.FragmentHomeBinding
import com.example.mvvmcoroutinesroomhiltsample.utils.Status
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var _binding: FragmentHomeBinding

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding

    private lateinit var adapter: ApiUserAdapter

    private lateinit var nadapter: NotesAdapter

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        init()
        setupUI()
        setupObserver()
        return root
    }

    private fun init() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet.root)
        bottomSheetBehavior.isHideable = true
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                shrinkExtendButton(newState == BottomSheetBehavior.STATE_EXPANDED)
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                Log.d("OnSlide", slideOffset.toString())
            }

        })
    }

    private fun setupUI() {
        _binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = ApiUserAdapter(arrayListOf())
        nadapter = NotesAdapter(arrayListOf())
        _binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                _binding.recyclerView.context,
                (_binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        //_binding.recyclerView.adapter = adapter
        _binding.recyclerView.adapter = nadapter

        binding.efabAdd.setOnClickListener {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        binding.bottomSheet.btnIcon.setOnClickListener {
            showMenu(it, R.menu.menu_icons)
        }

        binding.bottomSheet.btnAdd.setOnClickListener {
            // TODO need to add note to database
            homeViewModel.insert(Note(binding.bottomSheet.etAdd.text.toString(), resIcon))
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            binding.bottomSheet.etAdd.setText("")
        }
    }

    private fun shrinkExtendButton(shrink: Boolean) {
        if(shrink) {
            binding.efabAdd.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_close)
            binding.efabAdd.shrink()
        } else {
            binding.efabAdd.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_add)
            binding.efabAdd.text = getString(R.string.add_task)
            binding.efabAdd.extend()
        }
    }

    /*private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                MainRepository(
                    ApiHelperImpl(RetrofitBuilder.apiService),
                    DatabaseHelperImpl(DatabaseBuilder.getInstance(activity?.applicationContext!!))
                )
            )
        ).get(HomeViewModel::class.java)
    }*/

    @DrawableRes
    var resIcon: Int = 0

    //In the showMenu function from the previous example:
    @SuppressLint("RestrictedApi")
    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(context!!, v)
        popup.menuInflater.inflate(menuRes, popup.menu)
        popup.setOnMenuItemClickListener { menu_item ->
            when (menu_item.itemId) {
                R.id.icon1 -> {
                    resIcon = R.drawable.ic_round_warning
                }
                R.id.icon2 -> {
                    resIcon = R.drawable.ic_baseline_alarm
                }
                R.id.icon3 -> {
                    resIcon = R.drawable.ic_round_favorite
                }
            }
            true
        }
        if (popup.menu is MenuBuilder) {
            val menuBuilder = popup.menu as MenuBuilder
            menuBuilder.setOptionalIconsVisible(true)
            for (item in menuBuilder.visibleItems) {
                val iconMarginPx =
                    TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 10f, resources.displayMetrics
                    )
                        .toInt()
                if (item.icon != null) {
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                        item.icon = InsetDrawable(item.icon, iconMarginPx, 0, iconMarginPx, 0)
                    } else {
                        item.icon =
                            object : InsetDrawable(item.icon, iconMarginPx, 0, iconMarginPx, 0) {
                                override fun getIntrinsicWidth(): Int {
                                    return intrinsicHeight + iconMarginPx + iconMarginPx
                                }
                            }
                    }
                }
            }
        }
        popup.show()
    }

    private fun setupObserver() {
        activity?.let { it ->
            /*homeViewModel.getUsers().observe(it, {
                when (it.status) {
                    Status.SUCCESS -> {
                        _binding.progressBar.visibility = View.GONE
                        it.data?.let { users -> renderList(users) }
                        _binding.recyclerView.visibility = View.VISIBLE
                    }
                    Status.LOADING -> {
                        _binding.progressBar.visibility = View.VISIBLE
                        _binding.recyclerView.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        //Handle Error
                        _binding.progressBar.visibility = View.GONE
                        Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            })*/
            homeViewModel.getNotes().observe(it, {
                when (it.status) {
                    Status.SUCCESS -> {
                        _binding.progressBar.visibility = View.GONE
                        renderListNotes(it.data!!)
                        _binding.recyclerView.visibility = View.VISIBLE
                    }
                    Status.LOADING -> {
                        _binding.progressBar.visibility = View.VISIBLE
                        _binding.recyclerView.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        //Handle Error
                        _binding.progressBar.visibility = View.GONE
                        Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
    }

    private fun renderList(users: List<ApiUser>) {
        with(adapter) {
            addData(users)
            notifyDataSetChanged()
        }
    }

    private fun renderListNotes(notes: List<Note>) {
        with(nadapter) {
            addData(notes)
            notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}