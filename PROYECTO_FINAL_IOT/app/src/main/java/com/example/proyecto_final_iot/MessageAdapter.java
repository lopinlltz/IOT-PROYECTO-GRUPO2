package com.example.proyecto_final_iot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.Message;
import com.example.proyecto_final_iot.R;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> messagesList;
    private Context context;
    private static final int MSG_TYPE_SENT = 0;
    private static final int MSG_TYPE_RECEIVED = 1;

    public MessageAdapter(List<Message> messagesList, Context context) {
        this.messagesList = messagesList;
        this.context = context;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_SENT) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_message_sent, parent, false);
            return new MessageViewHolder(view, MSG_TYPE_SENT);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_message_received, parent, false);
            return new MessageViewHolder(view, MSG_TYPE_RECEIVED);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messagesList.get(position);
        holder.showMessage(message.getMessage(), message.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (messagesList.get(position).getSenderId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            return MSG_TYPE_SENT;
        } else {
            return MSG_TYPE_RECEIVED;
        }
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView messageTextView;
        public TextView messageTimeView;
        private int viewType;

        public MessageViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;

            if (viewType == MSG_TYPE_SENT) {
                messageTextView = itemView.findViewById(R.id.textViewMsgSent);
                messageTimeView = itemView.findViewById(R.id.textViewHourSent);
            } else {
                messageTextView = itemView.findViewById(R.id.textViewMsgReceived);
                messageTimeView = itemView.findViewById(R.id.textViewHourReceived);
            }
        }

        public void showMessage(String message, long timestamp) {
            messageTextView.setText(message);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            messageTimeView.setText(sdf.format(timestamp));
        }
    }
}