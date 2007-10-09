package com.yellowbkpk.gtf;

import java.io.Serializable;
import java.util.Currency;

import com.yellowbkpk.gtf.enums.PaymentMethodEnum;

public class FareAttributes implements Serializable {

	private static final long serialVersionUID = -6980967909162507067L;

	private final FareAttributes fareId;
	private final Double price;
	private final Currency currencyType;
	private final PaymentMethodEnum paymentMethod;
	private final Integer transfers;
	private Integer transferDuration;

	/**
	 * @param fareId
	 *            The fare_id field contains an ID that uniquely identifies a
	 *            fare class. The fare_id is dataset unique.
	 * @param price
	 *            The price field contains the fare price, in the unit specified
	 *            by currency_type.
	 * @param currencyType
	 *            The currency_type field defines the currency used to pay the
	 *            fare.
	 * @param paymentMethod
	 *            The payment_method field indicates when the fare must be paid.
	 * @param transfers
	 *            The transfers field specifies the number of transfers
	 *            permitted on this fare.
	 * @param transferDuration
	 *            The transfer_duration field specifies the length of time in
	 *            seconds before a transfer expires.
	 */
	public FareAttributes(FareAttributes fareId, Double price,
			Currency currencyType, PaymentMethodEnum paymentMethod,
			Integer transfers, Integer transferDuration) {
		this.fareId = fareId;
		this.price = price;
		this.currencyType = currencyType;
		this.paymentMethod = paymentMethod;
		this.transfers = transfers;
		this.transferDuration = transferDuration;
	}

	public FareAttributes getFareId() {
		return fareId;
	}

	public Double getPrice() {
		return price;
	}

	public Currency getCurrencyType() {
		return currencyType;
	}

	public PaymentMethodEnum getPaymentMethod() {
		return paymentMethod;
	}

	public Integer getTransfers() {
		return transfers;
	}

	public Integer getTransferDuration() {
		return transferDuration;
	}

	public String toString() {
		StringBuffer b = new StringBuffer();

		b.append(fareId.getFareId());
		b.append(",");
		b.append(price);
		b.append(",");
		b.append(paymentMethod);
		b.append(",");
		b.append(transfers);
		b.append(",");

		if (transferDuration == null) {
			b.append("");
		} else {
			b.append(transferDuration);
		}

		return b.toString();
	}
}
