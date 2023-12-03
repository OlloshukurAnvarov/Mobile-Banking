package com.leaf.mobilebanking.ui.adapter

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.leaf.mobilebanking.R
import com.leaf.mobilebanking.domain.entity.CardData
import com.leaf.mobilebanking.extensions.getLastFour
import com.leaf.mobilebanking.extensions.inflate
import com.leaf.mobilebanking.extensions.toAmount
import com.leaf.mobilebanking.extensions.validateDate
import kotlin.random.Random

class CardAdapter(private val list: ArrayList<CardData>) : RecyclerView.Adapter<CardViewHolder>() {
    private var onClickListener: ((Int) -> Unit)? = null

    fun setOnClickListener(clickListener: (Int) -> Unit) {
        onClickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder =
        CardViewHolder.create(parent, onClickListener)

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) =
        holder.bind(list[position])
}

class CardViewHolder(view: View, private val clickListener: ((Int) -> Unit)?) :
    RecyclerView.ViewHolder(view) {
    private val layout: CardView = view.findViewById(R.id.layout)
    private val lastFourNumberCard: TextView = view.findViewById(R.id.card_last_four_digits)
    private val cardValidDate: TextView = view.findViewById(R.id.card_valid_date)
    private val cardAmount: TextView = view.findViewById(R.id.card_amount)

    init {
        layout.setOnClickListener {
            clickListener?.invoke(bindingAdapterPosition)
        }
    }

    fun bind(card: CardData) {

        layout.setCardBackgroundColor(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val rand = Random
                val pR = rand.nextInt(30, 200)
                val pG = rand.nextInt(0, 100)
                val pB = rand.nextInt(0, 100)
                Color.rgb(pR, pG, pB)
            }else
                Color.parseColor("#3862F8")
        )
        lastFourNumberCard.text = card.pan.getLastFour()
        cardValidDate.text = validateDate(
            card.expireMonth, card.expireYear
        )
        cardAmount.text = card.amount.toAmount()
    }

    companion object {
        fun create(viewGroup: ViewGroup, clickListener: ((Int) -> Unit)?): CardViewHolder =
            CardViewHolder(viewGroup.inflate(R.layout.card_item), clickListener)
    }

}