package ru.ydn.orienteer.web.schema;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.wicketstuff.annotation.mount.MountPath;

import ru.ydn.orienteer.components.BootstrapType;
import ru.ydn.orienteer.components.FAIconType;
import ru.ydn.orienteer.components.OClassPageLink;
import ru.ydn.orienteer.components.commands.Command;
import ru.ydn.orienteer.components.commands.CreateOClassCommand;
import ru.ydn.orienteer.components.commands.DeleteOClassCommand;
import ru.ydn.orienteer.components.commands.ExportOSchemaCommand;
import ru.ydn.orienteer.components.commands.ImportOSchemaCommand;
import ru.ydn.orienteer.components.commands.ReloadOMetadataCommand;
import ru.ydn.orienteer.components.properties.DisplayMode;
import ru.ydn.orienteer.components.table.CheckBoxColumn;
import ru.ydn.orienteer.components.table.OClassColumn;
import ru.ydn.orienteer.components.table.OClassMetaColumn;
import ru.ydn.orienteer.components.table.OrienteerDataTable;
import ru.ydn.orienteer.web.BrowseClassPage;
import ru.ydn.orienteer.web.OrienteerBasePage;
import ru.ydn.wicket.wicketorientdb.model.OClassesDataProvider;
import ru.ydn.wicket.wicketorientdb.proto.OClassPrototyper;
import ru.ydn.wicket.wicketorientdb.security.OrientPermission;
import ru.ydn.wicket.wicketorientdb.security.RequiredOrientResource;
import ru.ydn.wicket.wicketorientdb.utils.OClassClassNameConverter;
import ru.ydn.wicket.wicketorientdb.utils.ODocumentORIDConverter;

import com.orientechnologies.orient.core.id.ORID;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.security.ODatabaseSecurityResources;
import com.orientechnologies.orient.core.record.impl.ODocument;

@MountPath("/classes")
@RequiredOrientResource(value = ODatabaseSecurityResources.SCHEMA, permissions=OrientPermission.READ)
public class ListOClassesPage extends OrienteerBasePage<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public ListOClassesPage()
	{
		super();
	}
	
	

	@Override
	public void initialize() {
		super.initialize();
		Form<?> form = new Form<Object>("form");
		List<IColumn<OClass, String>> columns = new ArrayList<IColumn<OClass,String>>();
		columns.add(new CheckBoxColumn<OClass, String, String>(null, OClassClassNameConverter.INSTANCE));
		columns.add(new OClassColumn<OClass>(new ResourceModel("class.name"), "name", ""));
		columns.add(new OClassMetaColumn(OClassPrototyper.SUPER_CLASS+".name", OClassPrototyper.SUPER_CLASS));
		columns.add(new OClassMetaColumn(OClassPrototyper.ABSTRACT, OClassPrototyper.ABSTRACT));
		columns.add(new OClassMetaColumn(OClassPrototyper.STRICT_MODE, OClassPrototyper.STRICT_MODE));
		columns.add(new PropertyColumn<OClass, String>(new ResourceModel("class.javaClass"), "javaClass", "javaClass"));
		columns.add(new PropertyColumn<OClass, String>(new ResourceModel("class.count"), "count", "count"));
		columns.add(new AbstractColumn<OClass, String>(new ResourceModel("class.browse")) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void populateItem(Item<ICellPopulator<OClass>> cellItem,
					String componentId, final IModel<OClass> rowModel) {
				cellItem.add(new Command<OClass>(componentId, "class.browse") {
					
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					protected AbstractLink newLink(String id) {
						return new OClassPageLink(id, rowModel, BrowseClassPage.class, DisplayMode.VIEW.asModel());
					}

					@Override
					public void onClick() {
						//We should not be here
					}
				}.setIcon(FAIconType.angle_double_down).setBootstrapType(BootstrapType.INFO));
				
			}
		});
		OClassesDataProvider provider = new OClassesDataProvider();
		provider.setSort("name", SortOrder.ASCENDING);
		OrienteerDataTable<OClass, String> table = new OrienteerDataTable<OClass, String>("table", columns, provider ,20);
		table.addCommand(new CreateOClassCommand(table));
		table.addCommand(new DeleteOClassCommand(table));
		table.addCommand(new ReloadOMetadataCommand(table));
		table.addCommand(new ExportOSchemaCommand(table));
		table.addCommand(new ImportOSchemaCommand(table));
		form.add(table);
		add(form);
	}



	@Override
	public IModel<String> getTitleModel() {
		return new ResourceModel("class.list.title");
	}

}
