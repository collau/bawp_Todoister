package com.bawp.todoister.adapter;

import com.bawp.todoister.model.Task;

/**
 * Created by junyi on 3/11/23
 */
public interface OnTodoClickListener {
    void onTodoClick(int adapterPosition, Task task);
}
