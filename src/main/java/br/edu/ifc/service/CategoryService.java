package br.edu.ifc.service;

import br.edu.ifc.entity.Category;
import br.edu.ifc.repository.CategoryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public final class CategoryService {

    @Inject
    CategoryRepository categoryRepository;

    public Category create(@Valid Category category) {
        this.categoryRepository.persistAndFlush(category);
        return category;
    }

    public Category getById(Long id) {
        Category category = this.categoryRepository.findById(id);
        if (category == null) {
            throw new NotFoundException("Categoria não encontrada");
        }
        return category;
    }

    public List<Category> get() {
        return this.categoryRepository.listAll();
    }

    public List<Category> get(String name) {
        if (name == null || name.isEmpty()) {
            return this.get();
        }
        String like = "%" + name.trim().toLowerCase() + "%";
        return this.categoryRepository.find("LOWER(name) LIKE ?1", like).list();
    }

    public long count() {
        return this.categoryRepository.count();
    }

    public Category update(long id, @Valid Category updated) {
        Category existing = this.getById(id);
        existing.setName(updated.getName());

        return this.categoryRepository.getEntityManager().merge(existing);
    }
}
