package ru.ydn.orienteer.components.properties;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import ru.ydn.orienteer.components.ODocumentPageLink;

import com.google.common.collect.Lists;
import com.orientechnologies.orient.core.db.record.OIdentifiable;

public class LinksCollectionViewPanel<T extends OIdentifiable, M extends Collection<T>> extends GenericPanel<M>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LinksCollectionViewPanel(String id, IModel<M> valueModel) {
		super(id, valueModel);
		add(new ListView<OIdentifiable>("links", getListModel()) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<OIdentifiable> item) {
				item.add(new ODocumentPageLink<OIdentifiable>("link", item.getModel()).setDocumentNameAsBody(true));
			}
		});
	}
	
	protected IModel<List<OIdentifiable>> getListModel()
	{
		return new LoadableDetachableModel<List<OIdentifiable>>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			@Override
			protected List<OIdentifiable> load() {
				Object value = getModel().getObject();
				if(value==null) return Collections.emptyList();
				else if(value instanceof List) return (List<OIdentifiable>) value;
				else if(value instanceof Iterable) return Lists.newArrayList((Iterable<OIdentifiable>)value);
				else return Lists.newArrayList((OIdentifiable)value);
			}
		};
	}

}
