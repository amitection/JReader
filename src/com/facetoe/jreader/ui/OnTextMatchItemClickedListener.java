package com.facetoe.jreader.ui;

import com.facetoe.jreader.githubapi.apiobjects.TextMatch;

/**
 * JReader
 * Created by facetoe on 22/01/14.
 */

/**
 * Listener for TextMatchItems.
 */
public interface OnTextMatchItemClickedListener {
    void textMatchItemClicked(TextMatch match);
}
