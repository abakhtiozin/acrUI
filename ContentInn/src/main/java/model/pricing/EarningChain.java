package main.java.model.pricing;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import main.java.model.Reseller;
import main.java.model.Supplier;
import main.java.model.searchParameters.HotelLocation;
import main.java.ui.webElements.searchResultsPage.RoomOffer;

public class EarningChain implements Iterable<HashMap<EarningChain.Component, Markup>> {

	public enum Component {
		SubSupplierMarkup,		
		ReverseCommission,
		Markup,
		DestinationMarkup,
		ReturnCommission,
		PayBackCommission,
		PayBackCommissionAgent,
		PaymentGatewayCommission,
		ResellerCommission,
		;
	}
		
	@SuppressWarnings("unused")
	private Markup cMarkup = null;
	@SuppressWarnings("serial")
	private HashMap<Integer, HashMap<Component, Markup>> chainComponents = new HashMap<Integer, HashMap<Component, Markup>>() {
		{
			put(100, new HashMap<EarningChain.Component, Markup>() {{
				put(Component.SubSupplierMarkup,			null);
			}});
			
			put(200, new HashMap<EarningChain.Component, Markup>() {{
				put(Component.ReverseCommission,			null);
				put(Component.Markup,						null);
				put(Component.DestinationMarkup,			null);
			}});
			
			put(300, new HashMap<EarningChain.Component, Markup>() {{
				put(Component.ReturnCommission,				null);
			}});
			
			put(400, new HashMap<EarningChain.Component, Markup>() {{
				put(Component.PayBackCommission,			null);
				put(Component.PayBackCommissionAgent,		null);
			}});
			
			put(500, new HashMap<EarningChain.Component, Markup>() {{
				put(Component.PaymentGatewayCommission,	null);
			}});
			
			put(600, new HashMap<EarningChain.Component, Markup>() {{
				put(Component.ResellerCommission,			null);
			}});
			
		}
	};
	private int maxPriority() {
		int m = Integer.MIN_VALUE;
		for (int k : chainComponents.keySet()) {
			if (k > m) m = k;
		}
		return m;		
	}
	@SuppressWarnings("unused")
	private int minPriority() {
		int m = Integer.MAX_VALUE;
		for (int k : chainComponents.keySet()) {
			if (k > m) m = k;
		}
		return m;
	}
	private int nextPriority(int currentPriority) {
		int p = maxPriority();
		for (int k : chainComponents.keySet()) {
			if (k > currentPriority) {
				if (k <= p) p = k;
			}
		}
		return p;
	}
	
	
	
	public EarningChain setComponent(Component chainType, Markup markup) {
		chainComponents.forEach((t, u) -> { 
				if (u.containsKey(chainType)) u.put(chainType, markup);  return; 
			}
		);
		return this;
	}	
	public Markup getComponent(Component chainComponent) {
		Markup cm = null;
		for (Entry<Integer, HashMap<Component, Markup>> c : chainComponents.entrySet()) {
			if (c.getValue().containsKey(chainComponent)) cm = c.getValue().get(chainComponent);
		}
		return cm;		
	}
	
	
	
	
	
	
	
	/*--------------- STATIC METHODS ---------------*/
	
	public static EarningChain defineChain(Reseller reseller, HotelLocation location, RoomOffer offer) {
		return defineChain(offer.supplier(), reseller, location);		
	}
	
	public static EarningChain defineChain(Supplier supplier, Reseller reseller, HotelLocation location) {
		EarningChain earningChain = new EarningChain();
		
		// SubSupplier Markup / Markup
		if (supplier.isSubSupplier()) {
			earningChain.setComponent(Component.SubSupplierMarkup, reseller.getMarkup(supplier));
			earningChain.setComponent(Component.Markup, reseller.getMarkup(supplier.getOriginalSupplier()));			
		}
		else earningChain.setComponent(Component.Markup, reseller.getMarkup(supplier));
		
		/* Destination markup */
		earningChain.setComponent(Component.DestinationMarkup, new DestinationMarkup(supplier).getActualMarkup(location, reseller));
		
		
		/* !!! Reverse commission: not implemented yet  */
		if (null != earningChain.getComponent(Component.Markup)) {
			throw new IllegalStateException("EarningChain.defineChain: Can't define the " + Component.ReverseCommission + " component: the " + Component.Markup + " component is defined; ");
		}
		//TODO: implement the logic
		
		
		
		/* !!! Return commission: not implemented yet */
		//TODO: implement the logic
		earningChain.setComponent(Component.ReturnCommission, reseller.getReturnCommission());		
		
		/* PayBack commissions */
		earningChain.setComponent(Component.PayBackCommission, reseller.getPayBackCommission());
		earningChain.setComponent(Component.PayBackCommissionAgent, reseller.getPayBackCommissionAgent());
		
		/* !!! Payment Gateway commission: not implemented yet */
		//TODO: implement the logic
		
		
		/* Reseller Own commission */
		earningChain.setComponent(Component.ResellerCommission, reseller.getDefaultOwnCommission(ResellerCommission.Type.content));
		
		
		return earningChain;
	}
	
	
		
	@Override
	public Iterator<HashMap<Component, Markup>> iterator() {
		return new Iterator<HashMap<Component, Markup>>() {
			
			private int cursor = Integer.MIN_VALUE;
			
			@Override
			public boolean hasNext() {
				return cursor < maxPriority();
			}
			
			@Override
			public HashMap<Component, Markup> next() {
				cursor = nextPriority(cursor);
				return chainComponents.get(cursor);
			}			
			
		};
		
		
	}
	
	
	
	
}
