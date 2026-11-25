package br.uel.ListaDeSupermercadoMobile.controller;

import br.uel.ListaDeSupermercadoMobile.model.Item;
import br.uel.ListaDeSupermercadoMobile.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/itens")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<Item> listar(@RequestParam(required = false) String nome,
                             @RequestParam(required = false) String campo,
                             @RequestParam(required = false, defaultValue = "asc") String ordem) {
        if (nome != null && !nome.isBlank()) {
            return itemService.buscarPorNome(nome);
        }
        if (campo != null && !campo.isBlank()) {
            return itemService.listarOrdenado(campo, ordem);
        }
        return itemService.listarTodos();
    }

    @GetMapping("/{id}")
    public Item buscarPorId(@PathVariable Long id) {
        return itemService.buscarPorId(id);
    }

    @PostMapping
    public Item criar(@Valid @RequestBody Item item) {
        return itemService.salvar(item);
    }

    @PutMapping("/{id}")
    public Item atualizar(@PathVariable Long id, @Valid @RequestBody Item item) {
        itemService.buscarPorId(id); // se não existir, lança a exceção
        item.setId(id);
        return itemService.salvar(item);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        itemService.excluir(id);
    }
}