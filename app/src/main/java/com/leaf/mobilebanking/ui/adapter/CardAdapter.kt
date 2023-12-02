package com.leaf.mobilebanking.ui.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.leaf.mobilebanking.R
import com.leaf.mobilebanking.domain.entity.CardData
import com.leaf.mobilebanking.extensions.inflate

class CardAdapter(private val list: ArrayList<CardData>) : RecyclerView.Adapter<CardViewHolder>() {
    private var onClickListener: ((Int) -> Unit)? = null

    fun setOnClickListener(clickListener: (Int) -> Unit){
        onClickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder =
        CardViewHolder(parent, onClickListener)

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) = holder.bind(list[position])
}

class CardViewHolder(view: View, private val clickListener: ((Int) -> Unit)?) : RecyclerView.ViewHolder(view) {
    private val layout: CardView = view.findViewById(R.id.layout)
    private val lastFourNumberCard: TextView = view.findViewById(R.id.card_last_four_digits)
    private val cardValidDate: TextView = view.findViewById(R.id.card_valid_date)
    private val cardAmount: TextView = view.findViewById(R.id.card_amount)

    init {
        layout.setOnClickListener {
            clickListener?.invoke(bindingAdapterPosition)
        }
    }

    fun bind(card: CardData){
        lastFourNumberCard.text = card.pan.drop(card.pan.length - (card.pan.length - 4))
        cardValidDate.text = "${card.expireMonth}/${card.expireYear % 100}"
        cardAmount.text = card.amount
    }

    companion object {
        fun create(viewGroup: ViewGroup, clickListener: ((Int) -> Unit)?): CardViewHolder =
            CardViewHolder(viewGroup.inflate(R.layout.card_item), clickListener)
    }

}