package app.com.uicollections.android.mvp_demo.func.entitiy;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NewsEntity {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("sortBy")
    @Expose
    private String sortBy;
    @SerializedName("articles")
    @Expose
    private List<NewsBean> articles = new ArrayList<>();

    /**
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return The source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source The source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return The sortBy
     */
    public String getSortBy() {
        return sortBy;
    }

    /**
     * @param sortBy The sortBy
     */
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    /**
     * @return The articles
     */
    public List<NewsBean> getArticles() {
        return articles;
    }

    /**
     * @param articles The articles
     */
    public void setArticles(List<NewsBean> articles) {
        this.articles = articles;
    }


    public static class NewsBean implements Parcelable {

        @SerializedName("author")
        @Expose
        private String author;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("urlToImage")
        @Expose
        private String urlToImage;
        @SerializedName("publishedAt")
        @Expose
        private String publishedAt;

        /**
         * @return The author
         */
        public String getAuthor() {
            return author;
        }

        /**
         * @param author The author
         */
        public void setAuthor(String author) {
            this.author = author;
        }

        /**
         * @return The title
         */
        public String getTitle() {
            return title;
        }

        /**
         * @param title The title
         */
        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * @return The description
         */
        public String getDescription() {
            return description;
        }

        /**
         * @param description The description
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * @return The url
         */
        public String getUrl() {
            return url;
        }

        /**
         * @param url The url
         */
        public void setUrl(String url) {
            this.url = url;
        }

        /**
         * @return The urlToImage
         */
        public String getUrlToImage() {
            return urlToImage;
        }

        /**
         * @param urlToImage The urlToImage
         */
        public void setUrlToImage(String urlToImage) {
            this.urlToImage = urlToImage;
        }

        /**
         * @return The publishedAt
         */
        public String getPublishedAt() {
            return publishedAt;
        }

        /**
         * @param publishedAt The publishedAt
         */
        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public NewsBean() {
        }

        protected NewsBean(Parcel in) {
            author = in.readString();
            title = in.readString();
            description = in.readString();
            url = in.readString();
            urlToImage = in.readString();
            publishedAt = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(author);
            dest.writeString(title);
            dest.writeString(description);
            dest.writeString(url);
            dest.writeString(urlToImage);
            dest.writeString(publishedAt);
        }

        @SuppressWarnings("unused")
        public static final Parcelable.Creator<NewsBean> CREATOR = new Parcelable.Creator<NewsBean>() {
            @Override
            public NewsBean createFromParcel(Parcel in) {
                return new NewsBean(in);
            }

            @Override
            public NewsBean[] newArray(int size) {
                return new NewsBean[size];
            }
        };
    }


}
