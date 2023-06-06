package com.ardev.api.actions

abstract class Action {

	abstract val actionId: String

    abstract val location: String

    abstract fun update(data: ActionData)

    abstract fun performAction(data: ActionData)
    
    val presentation = Presentation()
}