package com.danielstone.materialaboutlibrary.model

class MaterialAboutList(vararg mCards: MaterialAboutCard) {

    private val cards: ArrayList<MaterialAboutCard> = ArrayList()
    
    init {
        cards.addAll(mCards)
    }

    constructor() : this()

            
    fun addCard(card: MaterialAboutCard): MaterialAboutList {
        cards.add(card)
        return this
    }

    fun clearCards(): MaterialAboutList {
        cards.clear()
        return this
    }

    fun getCards(): ArrayList<MaterialAboutCard> = cards
     
}
