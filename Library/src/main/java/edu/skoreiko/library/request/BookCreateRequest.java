package edu.skoreiko.library.request;

import java.util.List;

/**
 * @author Rout
 * @version 1.0.0
 * @project Library
 * @class BookCreateRequest
 * @since 12.04.2026 - 12.06
 */
public record BookCreateRequest(String title, List<String> authors, String description) {}