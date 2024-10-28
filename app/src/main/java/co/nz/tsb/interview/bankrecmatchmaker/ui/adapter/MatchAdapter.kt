package co.nz.tsb.interview.bankrecmatchmaker.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.nz.tsb.interview.bankrecmatchmaker.R
import co.nz.tsb.interview.bankrecmatchmaker.data.model.MatchItem
import co.nz.tsb.interview.bankrecmatchmaker.widgets.CheckedListItem

class MatchAdapter(
  private val listener: OnItemClickListener
) : RecyclerView.Adapter<MatchAdapter.ViewHolder>() {
  private val _mutableItems = mutableListOf<MatchItem>()
  
  class ViewHolder(itemView: CheckedListItem, listener: OnItemClickListener?) :
    RecyclerView.ViewHolder(itemView) {
    private val mainText: TextView = itemView.findViewById(R.id.text_main)
    private val total: TextView = itemView.findViewById(R.id.text_total)
    private val subtextLeft: TextView = itemView.findViewById(R.id.text_sub_left)
    private val subtextRight: TextView = itemView.findViewById(R.id.text_sub_right)
    
    init {
      itemView.setOnClickListener { v: View ->
        if (listener != null) {
          val position = adapterPosition
          val matchItem = v.tag as? MatchItem
          if (position != RecyclerView.NO_POSITION && matchItem != null) {
            
            listener.onItemClick(matchItem.copy(isChecked = !itemView.isChecked))
          }
        }
      }
    }
    
    fun bind(matchItem: MatchItem) {
      mainText.text = matchItem.paidTo
      total.text = matchItem.total.toString()
      subtextLeft.text = matchItem.transactionDate
      subtextRight.text = matchItem.docType
      itemView.tag = matchItem
      (itemView as? CheckedListItem)?.isChecked = matchItem.isChecked
    }
  }
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    val listItem =
      layoutInflater.inflate(R.layout.list_item_match, parent, false) as CheckedListItem
    return ViewHolder(listItem, listener)
  }
  
  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val matchItem = _mutableItems[position]
    holder.bind(matchItem)
  }
  
  override fun getItemCount(): Int {
    return _mutableItems.size
  }
  
  fun updateItems(items: List<MatchItem>) {
    val diffCallback = MatchItemDiffCallback(_mutableItems, items)
    val diffResult = DiffUtil.calculateDiff(diffCallback)
    _mutableItems.clear()
    _mutableItems.addAll(items)
    diffResult.dispatchUpdatesTo(this)
  }
}

class MatchItemDiffCallback(
  private val oldList: List<MatchItem>,
  private val newList: List<MatchItem>
) : DiffUtil.Callback() {
  
  override fun getOldListSize(): Int {
    return oldList.size
  }
  
  override fun getNewListSize(): Int {
    return newList.size
  }
  
  override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
    oldList[oldItemPosition].id == newList[newItemPosition].id
  
  override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
    oldList[oldItemPosition] === newList[newItemPosition]
}

fun interface OnItemClickListener {
  fun onItemClick(item: MatchItem)
}