package org.mov.repository;

import org.mov.model.Theme;

import java.util.Collection;

public interface ThemeRepository {
    void saveTheme(Theme theme);

    void removeTheme(Theme theme);

    Theme findThemeById(Long id);

    Theme findThemeByName(String name);

    Collection<Theme> findAllThemes();
}
