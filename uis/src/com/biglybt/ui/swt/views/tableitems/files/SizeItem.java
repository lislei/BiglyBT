/*
 * Copyright (C) Azureus Software, Inc, All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details ( see the LICENSE file ).
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.biglybt.ui.swt.views.tableitems.files;

import com.biglybt.core.util.DisplayFormatters;
import com.biglybt.core.disk.DiskManagerFileInfo;
import com.biglybt.pif.ui.tables.*;
import com.biglybt.ui.swt.Utils;
import com.biglybt.ui.swt.views.FilesView;
import com.biglybt.ui.swt.views.table.CoreTableColumnSWT;
import com.biglybt.ui.swt.views.table.TableCellSWT;

/**
 *
 * @author TuxPaper
 * @since 2.0.8.5
 */
public class SizeItem
       extends CoreTableColumnSWT
       implements TableCellRefreshListener
{
  /** Default Constructor */
  public SizeItem() {
    super("size", ALIGN_TRAIL, POSITION_LAST, 70, TableManager.TABLE_TORRENT_FILES);
    setMinWidthAuto(true);
  }

	@Override
	public void fillTableColumnInfo(TableColumnInfo info) {
		info.addCategories(new String[] {
			CAT_BYTES,
		});
		info.setProficiency(TableColumnInfo.PROFICIENCY_BEGINNER);
	}

  @Override
  public void refresh(TableCell cell) {
    DiskManagerFileInfo fileInfo = (DiskManagerFileInfo)cell.getDataSource();
    
    boolean internal = fileInfo instanceof FilesView.FilesViewTreeNode && !((FilesView.FilesViewTreeNode)fileInfo).isLeaf();
    
    long value = (fileInfo == null) ? 0 : fileInfo.getLength();

    if( !cell.setSortValue( value ) && cell.isValid() ) {
      return;
    }

    String text;
    
    if ( value < 0 ){
    	
    	text = "";
    	
    }else{
    	
    	text = DisplayFormatters.formatByteCountToKiBEtc(value);
    	
    	if ( internal ){
    		
    		text = "(" + text + ")";
    	}
    }
    
    cell.setText( text );
    
	if (Utils.getUserMode() > 0 && (cell instanceof TableCellSWT)) {
		if (value >= 0x40000000l) {
			((TableCellSWT) cell).setTextAlpha(200 | 0x100);
		} else if (value < 0x100000) {
			((TableCellSWT) cell).setTextAlpha(180);
		} else {
			((TableCellSWT) cell).setTextAlpha(255);
		}
	}
  }
}
