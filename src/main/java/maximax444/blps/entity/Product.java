package maximax444.blps.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "product_id")
	private Long id;

	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name = "owner_id")
	private Customer owner;

	@Column(name = "price")
	private Long price;

	@Column(name = "product_name")
	private String name;

	@Column(name = "count")
	private Integer count;

	@Column(name = "status")
	private boolean status;


}
