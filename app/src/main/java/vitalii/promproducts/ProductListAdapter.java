package vitalii.promproducts;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import vitalii.promproducts.database.model.Product;

public class ProductListAdapter extends ArrayAdapter<Product> {

    private Activity activity;
    private List<Product> productList;

    public ProductListAdapter(Activity activity, int resource, List<Product> productList) {
        super(activity, resource, productList);
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Product product = getItem(position);

        holder.title.setText(product.getTitle());
        holder.description.setText(product.getDescription());
        holder.price.setText(Float.toString(product.getPrice()));

        if (product.getOld_price() != 0) {
            holder.old_price.setText(Float.toString(product.getOld_price()));
        }

        if (product.getImg_hires() == null || !product.getImg_hires().isEmpty()) {
            Picasso.with(activity).load(product.getImg_hires()).into(holder.img_hires);
        }

        return convertView;
    }

    private static class ViewHolder {
        private TextView title;
        private TextView description;
        private TextView price;
        private TextView old_price;
        private ImageView img_hires;
        //private ImageView img.hires_preview;

        private TextView authorName;
        private ImageView image;

        public ViewHolder(View v) {
            title = (TextView) v.findViewById(R.id.title);
            description = (TextView) v.findViewById(R.id.description);
            price = (TextView) v.findViewById(R.id.price);
            old_price = (TextView) v.findViewById(R.id.old_price);
            img_hires = (ImageView) v.findViewById(R.id.thumbnail);
        }
    }

}
