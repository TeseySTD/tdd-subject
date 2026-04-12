package edu.skoreiko.library.request;

import java.util.List;

/**
 * @author Rout
 * @version 1.0.0
 * @project Library
 * @class BookUpdateRequest
 * @since 12.04.2026 - 12.07
 */
public record BookUpdateRequest(String id, String title, List<String> authors, String description) {}