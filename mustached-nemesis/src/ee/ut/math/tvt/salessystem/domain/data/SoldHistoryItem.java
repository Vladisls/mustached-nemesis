package ee.ut.math.tvt.salessystem.domain.data;

import java.util.List;

@Entity
@Table(name = "HISTORYITEM")
public class SoldHistoryItem implements Cloneable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "DATE")
	private String date;
	@Column(name = "TIME")
	private String time;
	@Column(name = "TOTAL")
	private double total;
	@OneToMany(mappedBy = "historyitem")
	private List<SoldItem> soldItems;

	public SoldHistoryItem() {
	}

	public SoldHistoryItem(String date, String time, List<SoldItem> soldItems) {
		this.date = date;
		this.time = time;
		this.soldItems = soldItems;
		total = calcTotal();
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public double getTotal() {
		return total;
	}

	public List<SoldItem> getSoldItems() {
		return soldItems;
	}

	public double calcTotal() {
		double totalSum = 0;
		for (SoldItem el : soldItems) {
			totalSum += el.getSum();
		}
		return totalSum;
	}
}