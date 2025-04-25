import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Book(
    val title: String,
    val coverResId: Int,
    val rawResId: Int
): Parcelable