package com.klt.util

import java.util.Date


/** Interface for tasks entry's */
interface ITaskEntry {

    /** Interface for Deviations that can occur */
    interface IDeviation {
        val start: Date
        val end: Date
        val reason: String
    }

    val id: String
    val taskId: String
    val start: Date
    val end: Date
    val userId: String
    val deviations: Array<IDeviation>
}

/** Interface for task's */
interface ITask {

    /** Interface for goals in a task */
    interface IGoal {
        val name: String
        val value: Any
    }

    val id: String
    val completedAtLeastOnceToday: Boolean
    val taskName: String
    val goals: Array<IGoal>
}

/** Interface for customer */
interface ICustomer {
    val id: String
    val customerName: String
    val tasks: Array<ITask>
}