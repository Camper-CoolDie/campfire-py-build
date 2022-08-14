package org.campfire.python

public class Task {
    private var name = ""
    private var maxProgress: Int? = null
    private var progress = 0
    private var title = ""
    private var end = false

    constructor(name: String, maxProgress: Int? = null) {
        this.name = name
        this.maxProgress = maxProgress
        update()
    }

    fun update() {
        val rtitle = if(end) "done" else title
        if(maxProgress == null)
            println(String.format("%s... %s", name, rtitle))
        else
            println(String.format("%s [%4d/%4d]... %s", name, progress, maxProgress, rtitle))
        if(end)
            println()
    }

    fun add(title: String? = null) {
        if(this.maxProgress != null) {
            progress++
        }
        if(title != null)
            this.title = title
        update()
    }

    fun end() {
        this.end = true
        update()
    }
}